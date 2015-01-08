#!/usr/bin/env groovy
/**
 * modify properties values
 * 
 * rp.groovy file properties value
 * -----------------------------------------------------------------------
 */

Properties props = new Properties()
File propsFile = new File(args[0])
props.load(propsFile.newDataInputStream())
println "args[1]: ${args[1]}"
println "args[2]: ${args[2]}"

props.setProperty(args[1], args[2])
props.store(propsFile.newWriter(), null)