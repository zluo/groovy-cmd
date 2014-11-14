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

props.setProperty(args[1], args[2])
props.store(propsFile.newWriter(), null)