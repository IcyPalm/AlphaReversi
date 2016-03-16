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

ci: build
