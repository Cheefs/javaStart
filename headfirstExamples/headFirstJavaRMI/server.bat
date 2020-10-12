javac src/*  -d out

set CLASSPATH=C:\Users\user\Desktop\java\headFirstJavaRMI\out\
start rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false
java -cp out ServiceServerImpl

PAUSE