REM echo off
set SRCDIR=.\OPTI\src\
set BINDIR=.\OPTI\bin\
set BUILDDIR=.\OPTI\build\
set ASCIIDOCDIR=.\tools\asciidoc-8.6.9\
set SRCDOCDIR=.\srcdoc\
set DOCDIR=.\doc\

set MAKETEST=0

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
@echo // CRÉATION du fichier JAR
@echo ///////////////////////////////////////////////////////
jar cfm %BUILDDIR%OPTI.jar manifest.txt -C %BINDIR% .

@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // COMPILATION des tests
@echo ///////////////////////////////////////////////////////
if "%MAKETEST%"=="1" (
	javac -cp %SRCDIR% -d %BINDIR% %SRCDIR%MakeOptiWebTest.java
)

@echo.
@echo.
@echo.
@echo.
@echo.
@echo ///////////////////////////////////////////////////////
@echo // GÉNÉRATION de la Javadoc
@echo ///////////////////////////////////////////////////////
javadoc -quiet -cp ./build/lib/junit.jar -d ./doc/html -sourcepath ./src -subpackages fr


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
@echo // EXECUTION des tests
@echo ///////////////////////////////////////////////////////
if "%MAKETEST%"=="1" (
	cd %BUILDDIR%
	java MakeOptiWebTest
	cd %SPRINTDIR%
)

pause