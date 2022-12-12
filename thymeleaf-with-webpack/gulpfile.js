const gulp = require("gulp");
const named = require("vinyl-named");
const webpack = require("webpack-stream");
const environments = require("gulp-environments");
const postcss = require("gulp-postcss");
const sass = require("gulp-sass")(require("node-sass"));
const browserSync = require("browser-sync");
const htmlmin = require("gulp-html-minifier-terser");

browserSync.create();

const development = environments.development;
const production = environments.production;

const targetClassesDestination = "target/classes/";

/// HTML tasks
const htmlSource = ["src/main/resources/**/*.html"];
function copyHtmlTask(done) {
  gulp
    .src(htmlSource)
    .pipe(htmlmin({ collapseWhitespace: true }))
    .pipe(gulp.dest(targetClassesDestination));
  done();
}

/// CSS & scss tasks
const scssSources = [
  "src/main/resources/**/*.css",
  "src/main/resources/**/*.scss",
];
function copyScssTask(done) {
  gulp.src(scssSources).pipe(postcss()).pipe(sass()).pipe(gulp.dest(targetClassesDestination));
  done();
}

/// JS tasks
const jsSources = ["src/main/resources/static/js/**/*.js"];

const jsOutput = "target/classes/static/js";
function copyJsModern(done) {
  gulp
    .src(jsSources)
    .pipe(named())
    .pipe(
      webpack({
        devtool: development() ? "inline-source-map" : false,
        mode: development() ? "development" : "production",
        module: {
          rules: [
            {
              test: /\.js$/,
              exclude: /node_modules/,
              loader: "babel-loader",
              options: { presets: ["@babel/preset-env"] },
            },
          ],
        },
      })
    )
    .pipe(gulp.dest(jsOutput));
  done();
}

function watching_files() {
  browserSync.init({
    proxy: "localhost:8080", // default location for spring boot
    injectChanges: false,
    files: [
      "target/classes/templates",
      "target/classes/static/js",
      "target/classes/static/css",
    ],
  });
  gulp.watch(htmlSource, gulp.series(copyHtmlTask, copyScssTask));
  gulp.watch(scssSources, gulp.series(copyScssTask));
  gulp.watch(jsSources, gulp.series(copyJsModern));
}
gulp.task("watch", watching_files);
gulp.task("default", gulp.series("watch"));


gulp.task("build", gulp.series(copyHtmlTask, copyScssTask, copyJsModern));
