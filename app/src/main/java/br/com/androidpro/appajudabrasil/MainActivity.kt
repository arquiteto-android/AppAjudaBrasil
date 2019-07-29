package br.com.androidpro.appajudabrasil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var list: MutableList<Proposicao> = emptyList<Proposicao>().toMutableList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProposicoesAdapter
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)

        recyclerView = findViewById(R.id.rv_proposicoes)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = ProposicoesAdapter(list, this, requestQueue)
        recyclerView.adapter = adapter

        load()
    }

    private fun load() {

        val request = JsonObjectRequest(Request.Method.GET,
                "https://dadosabertos.camara.leg.br/api/v2/proposicoes?ordem=ASC&ordenarPor=id",
                null,
                Response.Listener<JSONObject> {

                    list.clear()
                    val dados = it.getJSONArray("dados")
                    for (i in 0 until dados.length()) {
                        val item = dados.getJSONObject(i)
                        list.add(Proposicao(item.getInt("id"),
                                item.getString("uri"),
                                item.getString("siglaTipo"),
                                item.getInt("ano"),
                                item.getString("ementa")))
                    }

                    adapter.notifyDataSetChanged()

                },
                null)

        requestQueue.add(request)
    }
}
