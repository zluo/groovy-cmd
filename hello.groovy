#!/usr/bin/env groovy
if (args)
{
 println "Hello ${args[0]}"
}
else
{
println "Hello Stranger"

}

println "ls -al ".execute().text
