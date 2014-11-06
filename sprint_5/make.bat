REM echo off
set SRCDIR=.\src\
set BINDIR=.\bin\
set BUILDDIR=.\build\
set ASCIIDOCDIR=.\tools\asciidoc-8.6.9\
set SRCDOCDIR=.\srcdoc\
set DOCDIR=.\doc\

set MAKETEST=1

@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // COMPILATION des executables
@echo ///////////////////////////////////////////////////////
if not exist %BINDIR% mkdir %BINDIR%
javac -cp %SRCDIR% -d %BINDIR% %SRCDIR%/fr/iut_blagnac/gui/Application.java



@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // CREATION du fichier JAR
@echo ///////////////////////////////////////////////////////
jar cfm %BUILDDIR%OPTI.jar manifest.txt -C %BINDIR% .

@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // GENERATION de la Javadoc
@echo ///////////////////////////////////////////////////////
if not exist %DOCDIR% mkdir %DOCDIR%
if not exist %DOCDIR%html mkdir %DOCDIR%html
javadoc -quiet -classpath ./build/lib/junit.jar -d ./doc/html -sourcepath ./src -subpackages fr


@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // COMPILATION des documentations
@echo ///////////////////////////////////////////////////////
python %ASCIIDOCDIR%asciidoc.py -b slidy -o"%DOCDIR%Presentation Finale.html" "%SRCDOCDIR%presentationFinale.txt"
python %ASCIIDOCDIR%asciidoc.py -o"%DOCDIR%Documentation Web.html" "%SRCDOCDIR%documentationWeb.adoc"
python %ASCIIDOCDIR%asciidoc.py -o"%DOCDIR%Documentation Technique.html" "%SRCDOCDIR%Documentation Technique.adoc"
python %ASCIIDOCDIR%asciidoc.py -o"%DOCDIR%Documentation Utilisateur.html" "%SRCDOCDIR%Documentation Utilisateur.adoc"

@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // COMPILATION des tests
@echo ///////////////////////////////////////////////////////
if "%MAKETEST%"=="1" (
	javac -cp %BINDIR%;%BUILDDIR%lib/junit.jar -d %BINDIR% %SRCDIR%fr/iut_blagnac/io/CSVManagerTest.java
	javac -cp %BINDIR%;%BUILDDIR%lib/junit.jar -d %BINDIR% %SRCDIR%fr/iut_blagnac/io/OptiElementManagerTest.java
	javac -cp %BINDIR%;%BUILDDIR%lib/junit.jar -d %BINDIR% %SRCDIR%fr/iut_blagnac/util/MakeOptiWebTest.java
)

@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // EXECUTION des tests
@echo ///////////////////////////////////////////////////////
if "%MAKETEST%"=="1" (
	cd %BINDIR%
	xcopy ..\build\*.csv .
	java -cp .;../%BUILDDIR%lib/junit.jar fr.iut_blagnac.io.CSVManagerTest
	java -cp .;../%BUILDDIR%lib/junit.jar fr.iut_blagnac.io.OptiElementManagerTest
	java -cp .;../%BUILDDIR%lib/junit.jar fr.iut_blagnac.util.MakeOptiWebTest
)

pause