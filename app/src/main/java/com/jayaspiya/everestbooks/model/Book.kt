package com.jayaspiya.everestbooks.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val Id: Int? = 0,
    val title: String? = null,
    val author: String? = null,
    val synopsis: String? = null,
    val imageUrl: String? = null,
    val price: Int? = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(Id)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(synopsis)
        parcel.writeString(imageUrl)
        parcel.writeValue(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}