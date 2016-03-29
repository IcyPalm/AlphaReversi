build: src
	mkdir -p bin
	javac \
		-d bin \
		-sourcepath src \
		src/alphareversi/Main.java

run: build
	java \
		-classpath bin \
		alphareversi.Main

checkstyle: checkstyle.xml build.xml src
	ant checkstyle

ci: build checkstyle
