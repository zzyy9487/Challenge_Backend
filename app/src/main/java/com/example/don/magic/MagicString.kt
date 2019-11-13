package com.example.don.magic

data class MagicString(var result:String, var user: ShopUser?, var magics:MutableList<SQLMagic?> ) {
}
