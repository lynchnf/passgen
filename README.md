# Passgen

Utility for generating random passwords and validating user entered passwords. 

## To Build

To checkout Passgen and compile it, execute the following commands:

    cd <workspace-dir>
    git clone git@github.com:lynchnf/passgen.git
    cd passgen
    git checkout master
    mvn clean install

These commands will produce 2 artifacts:

**passgen-core** - A jar file installed in your local maven repository. It can be included in other projects where you
need to generate random passwords or validate supplied passwords. To add to your other project's dependencies, use the
following in your `pom.xml` file:

    <dependency>
        <groupId>norman</groupId>
        <artifactId>passgen-core</artifactId>
        <version>2.x.x</version>
    </dependency>

**passgen-ui** - A pair of compressed archive files (tar.gz and zip formats for Linux and Windows machines
respectively) containing everything needed to install a Java Swing app that will generate random passwords.

## To Install

To install the Passgen app on a Linux machine, copy file `passgen-ui-2.x.x-bin.tar.gz` to your target directory and
execute the following command:

    tar -xzf passgen-ui-2.x.x-bin.tar.gz

On a Windows machine, copy file `passgen-ui-2.x.x-bin.zip` to your target directory and unzip it.

When done, this will produce a directory named `passgen` which will contain everything needed to run the Passgen app.
    
## To Run

To run the Passgen app, execute `./passgen/bin/passgen.sh` on a Linux machine. (Execute `passgen\bin\passgen.bat`
on a Windows machine.) When the window pops up, enter the desired settings and press the Generate Password button.

**Number of Passwords** - The number of passwords to generate. Can be a number between 1 and 99.

**Password Length** - The length of the generated passwords. Can be a number between 1 and 99.

**Allow Number for First Character** - If this box is checked, the generated password might begin with a numeric digit.
If not checked, it will never begin with digit.

**Include Lower Case Characters** - If this box is checked, the generated password will include lower case alphabetic
characters. If not, it will not.

**Include Upper Case Characters** - If this box is checked, the generated password will include upper case alphabetic
characters. If not, it will not.

**Include Numeric Characters** - If this box is checked, the generated password will include numeric characters. If not,
it will not.

**Include Special Characters** - If this box is checked, the generated password will include special characters. If not,
it will not.

**Generate Password** - When the desired settings are entered, press this button to create the selected number of
randomly generated passwords.

**Copy to Clipboard** - After your passwords are generated, press this button to copy them to the clipboard.

## Attribution

Clipboard icon (clipboard-regular.svg) downloaded from [Font Awesome](https://fontawesome.com/license), then converted
to a .png file.
