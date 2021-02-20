package com.hrishi.chatapplication.models

class Users() {
    var displayName:String?=null
    var image:String?=null
    var status:String?=null

    constructor(displayName: String?, image: String?, status: String?) : this()  {
        this.displayName = displayName
        this.image = image
        this.status = status
    }
}