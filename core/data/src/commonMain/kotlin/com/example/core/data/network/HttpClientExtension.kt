package com.example.core.data.network

//full url or relative paths (api)
fun constructRoute(route:String): String{
    return when{
        //full route was passed return route
        route.contains(UrlConstants.BASE_URL_HTTPS) -> route
        //relative path was used
        route.startsWith("/") -> "${UrlConstants.BASE_URL_HTTPS}$route"
        else -> "${UrlConstants.BASE_URL_HTTPS}/$route"
    }
}