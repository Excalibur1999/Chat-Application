package com.hrishi.chatapplication.models

import java.lang.StringBuilder

class FriendlyMessage() {
    var id:String?=null
    var name:String?=null
    var text:String?=null
    constructor(id:String,name:String,text:String) : this() {
        this.id=id
        this.name=name
        this.text=text
    }
}