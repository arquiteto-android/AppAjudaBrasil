package br.com.androidpro.appajudabrasil

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ProposicoesAdapter (val list: List<Proposicao>, val context: Context, val requestQueue: RequestQueue) : RecyclerView.Adapter<ProposicoesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_proposicao, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proposicao = list[position]
        holder.apply {
            siglaTipo.text = proposicao.siglaTipo
            ementa.text = proposicao.ementa
            ano.text = proposicao.ano.toString()
            load(proposicao.uri, holder)
        }

    }

    private fun load(uri: String, holder: ViewHolder) {

        val request = JsonObjectRequest(Request.Method.GET,
                uri,
                null,
                Response.Listener<JSONObject> {
                    val dados = it.getJSONObject("dados")
                    holder.verMais.setOnClickListener {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(dados.getString("urlInteiroTeor")))
                        context.startActivity(intent)
                    }

                },
                null)

        requestQueue.add(request)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val siglaTipo: TextView = itemView.findViewById(R.id.tv_sigla_tipo)
        val ementa: TextView = itemView.findViewById(R.id.tv_ementa)
        val ano: TextView = itemView.findViewById(R.id.tv_ano)
        val verMais: Button = itemView.findViewById(R.id.btn_ver_mais)
    }
}