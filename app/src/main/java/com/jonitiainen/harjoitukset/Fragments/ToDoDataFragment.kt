package com.jonitiainen.harjoitukset.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.jonitiainen.harjoitukset.ToDo
import com.jonitiainen.harjoitukset.databinding.FragmentToDoDataBinding
import org.json.JSONObject

class ToDoDataFragment : Fragment() {
    private var _binding: FragmentToDoDataBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentToDoDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // btnGetToDoData listen for click
        binding.btnGetToDoData.setOnClickListener {
            getToDos()
        }

        // btnPostToDoData listen for click
        binding.btnPostToDoData.setOnClickListener {
            postToDos()
        }

        return root
    }

    // Request a string response from the provided URL.
    private fun getToDos() {
        Log.d("TESTI", "getToDos() kutsuttu")

        // this is the url where we want to get our data from
        val JSON_URL = "https://jsonplaceholder.typicode.com/todos"

        // haetaan GSON-objekti käytettäväksi
        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                // Log.d("TESTI", response)

                var rows : List<ToDo> = gson.fromJson(response, Array<ToDo>::class.java).toList()

                // kokeillaan käyttää rows-listaa, saadaanko dataa ulos
                Log.d("TESTI", "Kommenttien määrä: " + rows.size)

                // kokeillaan loopat läpi kaikki kommentit, tulostetaan jokainen email
                for (item : ToDo in rows) {
                    Log.d("TESTI", item.title.toString())
                }
            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("TESTI", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    // POST request into the provided URL.
    private fun postToDos() {
        Log.d("TESTI", "postToDos() kutsuttu")

        // this is the url where we want to get our data from
        val JSON_URL = "https://jsonplaceholder.typicode.com/todos"

        // haetaan GSON-objekti käytettäväksi
        val gson = GsonBuilder().setPrettyPrinting().create()

        // JSON object with test data to send to the url server
        val postData = JSONObject()
        postData.put("userId", 1000)
        postData.put("id", 1001)
        postData.put("title", "post test")
        postData.put("completed", true)

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                Log.d("TESTI", response)

                var row : ToDo = gson.fromJson(response, ToDo::class.java)

                // kokeillaan käyttää rows-listaa, saadaanko dataa ulos
                Log.d("TESTI", "Title: " + row.title)
            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("TESTI", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                // convert the JSON object to a byte array
                return postData.toString().toByteArray(Charsets.UTF_8)
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

