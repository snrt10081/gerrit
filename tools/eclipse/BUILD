load('//tools/bzl:pkg_war.bzl', 'LIBS', 'PGMLIBS')
load('//tools/bzl:classpath.bzl', 'classpath_collector')

PROVIDED_DEPS = [
  '//lib/bouncycastle:bcprov',
  '//lib/bouncycastle:bcpg',
  '//lib/bouncycastle:bcpkix',
]

TEST_DEPS = [
  '//gerrit-gpg:gpg_tests',
  '//gerrit-gwtui:ui_tests',
  '//gerrit-httpd:httpd_tests',
  '//gerrit-patch-jgit:jgit_patch_tests',
  '//gerrit-reviewdb:client_tests',
  '//gerrit-server:server_tests',
]

DEPS = [
  '//gerrit-acceptance-tests:lib',
  '//gerrit-gwtdebug:gwtdebug',
  '//gerrit-gwtui:ui_module',
  '//gerrit-main:main_lib',
  '//gerrit-plugin-gwtui:gwtui-api-lib',
  '//gerrit-server:server',
  # TODO(davido): figure out why it's not reached through test dependencies
  '//lib:jimfs',
  '//lib/asciidoctor:asciidoc_lib',
  '//lib/asciidoctor:doc_indexer_lib',
  '//lib/auto:auto-value',
  '//lib/gwt:ant',
  '//lib/gwt:colt',
  '//lib/gwt:javax-validation',
  '//lib/gwt:javax-validation_src',
  '//lib/gwt:jsinterop-annotations',
  '//lib/gwt:jsinterop-annotations_src',
  '//lib/gwt:tapestry',
  '//lib/gwt:w3c-css-sac',
  '//lib/jetty:servlets',
  '//lib/prolog:compiler_lib',
  # TODO(davido): I do not understand why it must be on the Eclipse classpath
  #'//Documentation:index',
]

java_library(
  name = 'classpath',
  runtime_deps = LIBS + PGMLIBS + DEPS,
  testonly = 1,
)

classpath_collector(
  name = 'main_classpath_collect',
  deps = LIBS + PGMLIBS + DEPS + PROVIDED_DEPS,
  testonly = 1,
  # TODO(davido): Handle plugins
  #scan_plugins(),
)

classpath_collector(
  name = "gwt_classpath_collect",
  deps = ["//gerrit-gwtui:ui_module"],
)

classpath_collector(
  name = "autovalue_classpath_collect",
  deps = ["//lib/auto:auto-value"],
)