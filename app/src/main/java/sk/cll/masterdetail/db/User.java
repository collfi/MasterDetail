package sk.cll.masterdetail.db;

//@Entity(tableName = "users")
public class User /*implements Parcelable*/ {

    /*public User() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "first_name")
    private String name;

    @SerializedName("surname")
    @Expose
    @ColumnInfo(name = "sur_name")
    private String surname;

    @SerializedName("gender")
    @Expose
    @ColumnInfo(name = "gender")
    private String gender;

    @SerializedName("region")
    @Expose
    @ColumnInfo(name = "region")
    private String region;

    @SerializedName("age")
    @Expose
    @ColumnInfo(name = "age")
    private int age;

    @SerializedName("phone")
    @Expose
    @ColumnInfo(name = "phone")
    private String phone;

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("photo")
    @Expose
    @ColumnInfo(name = "photo")
    private String photo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeInt(age);
        dest.writeString(gender);
        dest.writeString(region);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(photo);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surname = in.readString();
        age = in.readInt();
        gender = in.readString();
        region = in.readString();
        phone = in.readString();
        email = in.readString();
        photo = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender.substring(0, 1).toUpperCase().concat(gender.substring(1));
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFullName() {
        return getName().concat(" ").concat(getSurname());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @NonNull
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }*/
}
