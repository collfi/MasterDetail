package sk.cll.masterdetail.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class KUser(

        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @SerializedName("name")
        @Expose
        @ColumnInfo(name = "first_name")
        val name: String,

        @SerializedName("surname")
        @Expose
        @ColumnInfo(name = "sur_name")
        val surname: String,

//        @SerializedName("gender")
//        @Expose
//        @ColumnInfo(name = "gender")
//        val gender1: String,

        @SerializedName("age")
        @Expose
        @ColumnInfo(name = "age")
        val age: Int,

        @SerializedName("phone")
        @Expose
        @ColumnInfo(name = "phone")
        val phone: String,

        @SerializedName("email")
        @Expose
        @ColumnInfo(name = "email")
        val email: String,

        @SerializedName("photo")
        @Expose
        @ColumnInfo(name = "photo")
        val photo: String,

        @SerializedName("region")
        @Expose
        @ColumnInfo(name = "region")
        val region: String

) : Parcelable {

    @SerializedName("gender")
    @Expose
    @ColumnInfo(name = "gender")
    var gender: String? = null
        get() = field?.substring(0, 1)?.toUpperCase().plus(field?.substring(1))


    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
        parcel.readString()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeInt(age)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(photo)
        parcel.writeString(region)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KUser> {
        override fun createFromParcel(parcel: Parcel): KUser {
            return KUser(parcel)
        }

        override fun newArray(size: Int): Array<KUser?> {
            return arrayOfNulls(size)
        }
    }

    fun getFullName(): String {
        return "$name $surname"
    }
}