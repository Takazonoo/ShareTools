#!/bin/bash

# Atualizar listas de pacotes
sudo apt update

# Instalar Java e Ant
sudo apt install -y default-jdk ant wget

# Criar diretório lib se não existir
mkdir -p lib

# Baixar o driver JDBC do MySQL
MYSQL_JDBC_VERSION="8.0.33"
MYSQL_JDBC_JAR="mysql-connector-java-${MYSQL_JDBC_VERSION}.jar"
MYSQL_JDBC_URL="https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_JDBC_VERSION}/${MYSQL_JDBC_JAR}"

# Usar wget para baixar o driver
wget -P lib ${MYSQL_JDBC_URL}

# Criar build.xml se não existir
if [ ! -f build.xml ]; then
    cat <<EOL > build.xml
<?xml version="1.0" encoding="UTF-8"?>
<project name="ShareTools" default="default" basedir=".">
    <description>Builds, tests, and runs the project ShareTools.</description>

    <!-- Definir propriedades -->
    <property name="src.dir" value="src"/>
    <property name="build.dir" value="build"/>
    <property name="dist.dir" value="dist"/>
    <property name="lib.dir" value="lib"/>
    <property name="mysql.driver.jar" value="\${lib.dir}/${MYSQL_JDBC_JAR}"/>

    <!-- Garantir que os diretórios necessários existam -->
    <target name="init">
        <mkdir dir="\${build.dir}"/>
        <mkdir dir="\${dist.dir}"/>
    </target>

    <!-- Compilar o código Java -->
    <target name="compile" depends="init">
        <javac srcdir="\${src.dir}" destdir="\${build.dir}">
            <classpath>
                <fileset dir="\${lib.dir}">
                    <include name="**/*.jar"/>
                </fileset>
            </classpath>
        </javac>
    </target>

    <!-- Criar o arquivo JAR -->
    <target name="jar" depends="compile">
        <jar destfile="\${dist.dir}/ShareTools.jar" basedir="\${build.dir}">
            <manifest>
                <attribute name="Main-Class" value="MainClass"/> <!-- Ajustar conforme sua classe principal -->
            </manifest>
        </jar>
    </target>

    <!-- Executar o projeto -->
    <target name="run" depends="compile">
        <java fork="true" classname="MainClass" classpath="\${build.dir}:\${mysql.driver.jar}">
            <classpath>
                <pathelement path="\${build.dir}"/>
                <fileset dir="\${lib.dir}">
                    <include name="**/*.jar"/>
                </fileset>
            </classpath>
        </java>
    </target>

    <!-- Limpar o diretório de build -->
    <target name="clean">
        <delete dir="\${build.dir}"/>
        <delete dir="\${dist.dir}"/>
    </target>

    <!-- Target padrão -->
    <target name="default" depends="jar"/>
</project>
EOL
fi

# Executar targets do Ant
ant clean
ant compile
ant jar
ant run
