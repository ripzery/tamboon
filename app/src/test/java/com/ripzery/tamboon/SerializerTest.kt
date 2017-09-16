package com.ripzery.tamboon

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ripzery.tamboon.data.Tamboon
import org.junit.Assert
import org.junit.Test

/**
 * Created by ripzery on 9/16/17.
 */
class SerializerTest {
    private val JSON_RESPONSE = "[\n" +
            "    {\n" +
            "        \"id\": 0,\n" +
            "        \"name\": \"Ban Khru Noi\",\n" +
            "        \"logo_url\": \"http://rkdretailiq.com/news/img-corporate-baankrunoi.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Habitat for Humanity Thailand\",\n" +
            "        \"logo_url\": \"http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"Paper Ranger\",\n" +
            "        \"logo_url\": \"https://myfreezer.files.wordpress.com/2007/06/paperranger.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"Makhampom\",\n" +
            "        \"logo_url\": \"http://www.makhampom.net/makhampom/ppcms/uploads/UserFiles/Image/Thai/T14Publice/2554/January/Newyear/logoweb.jpg\"\n" +
            "    }\n" +
            "]"

    private val mGson = Gson()

    infix fun Any.shouldEqual(a: Any){
        Assert.assertEquals(this, a)
    }

    @Test
    fun shouldSerialize() {
        val listType = object : TypeToken<List<Tamboon.Charity>>() {}.type
        val charitiesList = mGson.fromJson<List<Tamboon.Charity>>(JSON_RESPONSE, listType)

        charitiesList.size shouldEqual 4
        charitiesList[0].name shouldEqual "Ban Khru Noi"
        charitiesList[1].name shouldEqual "Habitat for Humanity Thailand"
        charitiesList[2].name shouldEqual "Paper Ranger"
        charitiesList[3].name shouldEqual "Makhampom"
    }
}