package com.jonitiainen.harjoitukset

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jonitiainen.harjoitukset.fragments.ToDoDataFragmentDirections
import com.jonitiainen.harjoitukset.databinding.RecyclerviewItemRowBinding
import com.jonitiainen.harjoitukset.datatypes.todo.ToDo

// aloitetaan luomalla uusi luokka CommentHolder
class ToDoAdapter(private val todos: List<ToDo>) : RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {
    // tähän väliin tulee kaikki RecyclerView-adapterin vaatimat metodit
    // kuten onCreateViewHolder, onBindViewHolder sekä getItemCount

    // binding layerin muuttujien alustaminen
    private var _binding: RecyclerviewItemRowBinding? = null
    private val binding get() = _binding!!


    // ViewHolderin onCreate-metodi. käytännössä tässä kytketään binding layer
    // osaksi CommentHolder-luokkaan (adapterin sisäinen luokka)
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        // binding layerina toimii yksitätinen recyclerview_item_row.xml -instanssi
        _binding = RecyclerviewItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoHolder(binding)
    }

    // tämä metodi kytkee yksittäisen ToDoc-objektin yksittäisen ToDoHolder-instanssiin
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä onBindViewHolder
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val itemComment = todos[position]
        holder.bindComment(itemComment)
    }

    // Adapterin täytyy pysty tietämään sisältämänsä datan koko tämän metodin avulla
    // koska CommentAdapter pohjautuu RecyclerViewin perusadapteriin, täytyy tästä
    // luokasta löytyä metodi nimeltä getItemCount
    override fun getItemCount(): Int {
        return todos.size
    }


    // ToDoHolder, joka määritettiin oman ToDoAdapterin perusmäärityksessä (ks. luokan yläosa)
    // Holder-luokka sisältää logiikan, jolla data ja ulkoasu kytketään toisiinsa
    class ToDoHolder(v: RecyclerviewItemRowBinding) : RecyclerView.ViewHolder(v.root), View.OnClickListener {

        // tämän kommentin ulkoasu ja varsinainen data
        private var view: RecyclerviewItemRowBinding = v
        private var todo: ToDo? = null

        // mahdollistetaan yksittäisen itemin klikkaaminen tässä luokassa
        init {
            // tämä mahdollistaa sen, että kun kommenttia klikataan
            // suoritetaan alta löytyvä onClick
            v.root.setOnClickListener(this)
        }

        // metodi, joka kytkee datan yksityiskohdat ulkoasun yksityiskohtiin
        fun bindComment(todo : ToDo)
        {
            // laitetaan yksittäinen comment-data talteen myöhempää käyttöä varten
            // ks.onclick
            this.todo = todo

            // asetetaan eli "mäpätään" jokainen kommentin data
            // tallennetaan kommentin nimi muuttujaan
            var text : String = todo.title.toString()
            var isChecked : Boolean? = todo.completed

            // ? tarkoittaa, että jos text olisi null niin se koodi skipataa
            // jos liian pitkä teksti, lyhennetää ja asetetaan kolme pistettä perään
            if(text.length > 20) {
                text = text.substring(0, 20) + "..."
            }

            // aseta muuttujassa oleva nimi textViewiin
            view.textviewTodoTask.text = text

            // jos completed on true niin näytä done kuva, jos ei nii näytä not_done kuva
            if (isChecked != null) {
                when (isChecked) {
                    true -> {
                        view.imageViewTaskDone.visibility = View.VISIBLE
                        view.imageViewTaskNotDone.visibility = View.INVISIBLE
                    }
                    else -> {
                        view.imageViewTaskDone.visibility = View.INVISIBLE
                        view.imageViewTaskNotDone.visibility = View.VISIBLE
                    }
                }
            }

        }

        // jos itemiä klikataan käyttöliittymässä, ajetaan tämä koodi
        override fun onClick(v: View) {
            Log.d("TESTI", "RecyclerView-ToDo -klikattu!")
            Log.d("TESTI", "Todon ID = " + todo?.id.toString())

            // haetaan action comment detail fragmenttiin, klikatun kommentin id paramtri
            val action = ToDoDataFragmentDirections.actionToDoDataFragmentToToDoDetailFragment(todo?.id as Int)
            v.findNavController().navigate(action)
        }
    }
}