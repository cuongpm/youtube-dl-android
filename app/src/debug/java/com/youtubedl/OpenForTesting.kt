package com.youtubedl

/**
 * Created by cuongpm on 1/31/19.
 */

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass

@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting