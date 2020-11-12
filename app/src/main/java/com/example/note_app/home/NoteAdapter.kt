package com.example.note_app.home


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app.R


/**
 * Copyright 2019 (C) Xplo
 *
 * Created  : 6/17/2019
 * Author   : Xplo
 * Version  : 1.0
 * Desc     :
 * Comment  :
 */


class NoteAdapter(
    var mDataset: ArrayList<NoteModel>
) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(), AdapterView.OnItemClickListener, View.OnLongClickListener{

    var mOnItemClickListener:OnItemClickListener?=null
    var mOnIemLongClickListener:OnIemLongClickListener?=null

    var position:Int=0
    companion object {
        private const val TAG = "noteAdapter"
    }




    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {


//        val sliderDotspanel : LinearLayout = v.findViewById(R.id.slider_dots)

        var titleTv:TextView=v.findViewById(R.id.title)
        var date:TextView=v.findViewById(R.id.date)



        init {
            v.setOnClickListener(this)
            v.setOnLongClickListener(this@NoteAdapter)
        }

        @SuppressLint("ResourceAsColor")
        fun bind(item: NoteModel) {
            titleTv.text=item.text.toString()
            date.text=item.date.toString()


        }



        override fun onClick(v: View?) {
//            Log.d("aaa","item clicked")
            mOnItemClickListener?.onItemClickListener(getItem(adapterPosition))
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get element from your dataset at this position
        val item = mDataset[position]
        holder.bind(item)

        this@NoteAdapter.position = holder.adapterPosition


    }

    //get a specific array list item
    fun getItem(n: Int): NoteModel {
        return mDataset[n]
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mOnItemClickListener?.onItemClickListener(mDataset[position])
    }
    override fun onLongClick(v: View?): Boolean {
        mOnIemLongClickListener?.onItemLongClickListener(mDataset[position])
        return true

    }



    fun removeItem(noteModel: NoteModel) {
        mDataset.remove(noteModel)
        notifyDataSetChanged()
    }

//    private fun showDialog(
//        activity: Activity?, post: Post
//    ) {
//        val builder = AlertDialog.Builder(activity)
//        builder.setMessage("Are you sure?")
//            .setCancelable(false)
//            .setPositiveButton(
//                "Yes"
//            ) { dialog, id ->
//                deleteAPost(post!!)
//            }
//            .setNegativeButton(
//                "No"
//            ) { dialog, id -> dialog.cancel() }
//        val alert = builder.create()
//        alert.show()
//    }

    interface OnItemClickListener {
        fun onItemClickListener(noteModel: NoteModel)
    }

    interface OnIemLongClickListener{
        fun onItemLongClickListener(noteModel: NoteModel)
    }

    fun setOnItemLongClickListener(mOnIemLongClickListener:OnIemLongClickListener){
        this.mOnIemLongClickListener=mOnIemLongClickListener
    }

    fun setOnItemClickListener(mOnItemClickListener:OnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener
    }



}