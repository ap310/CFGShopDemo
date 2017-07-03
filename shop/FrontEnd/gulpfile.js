var version = '1.0.5';
var gulp = require('gulp');
var rename = require('gulp-rename');

gulp.task('default', ['sass']);

gulp.task('watch', function() {
  gulp.watch(paths.sass, ['sass']);
});


// minify html
var minifyHtml = require('gulp-minify-html');
var templateCache = require('gulp-angular-templatecache');
gulp.task('app-html', function() {
  gulp.src(['www/templates/**/*.html','!www/templates/public/*.html'])
    .pipe(minifyHtml({empty:true}))
    .pipe(templateCache({
      filename: 'templates.v'+version+'.min.js',
      standalone: true,
      root: 'templates'
    }))
    .pipe(gulp.dest('www/js'));
  gulp.src(['www/templates/**/*.html','!www/templates/app/*.html'])
    .pipe(minifyHtml({empty:true}))
    .pipe(templateCache({
      filename: 'templatesPublic.v'+version+'.min.js',
      standalone: true,
      root: 'templates'
    }))
    .pipe(gulp.dest('www/js'));
});

// minify js
var closureCompiler = require('google-closure-compiler').gulp();

gulp.task('app-js', function () {
  gulp.src(['www/js/source/app/*.js','www/js/source/common/*.js'])
    .pipe(closureCompiler({
      compilation_level: 'SIMPLE_OPTIMIZATIONS',
      js_output_file: 'dsbx.v'+version+'.min.js'
    }))
    .pipe(gulp.dest('www/js'));
  gulp.src(['www/js/source/public/*.js','www/js/source/common/*.js'])
    .pipe(closureCompiler({
      compilation_level: 'SIMPLE_OPTIMIZATIONS',
      js_output_file: 'dsbxPublic.v'+version+'.min.js'
    }))
    .pipe(gulp.dest('www/js'));
});

// minify css
var cleanCSS = require('gulp-clean-css');

gulp.task('app-css', function() {
  return gulp.src('www/css/source/*.css')
    .pipe(cleanCSS({compatibility: 'ie8'}))
    .pipe(rename({
      suffix: '.v'+version+'.min'
    }))
    .pipe(gulp.dest('www/css'));
});

// minify all
gulp.task('app',['app-html','app-js','app-css']);