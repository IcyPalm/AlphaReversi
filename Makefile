build: src build.xml
	ant compile

run: build
	java \
		-verbose:gc \
		-XX:+UseConcMarkSweepGC \
		-XX:+CMSIncrementalMode \
		-classpath bin \
		alphareversi.Main

checkstyle: checkstyle.xml build.xml src
	ant checkstyle

test: build
	ant test

ci: test checkstyle
