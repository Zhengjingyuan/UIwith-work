package com.example.zhongchou3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zhongchou3.Adapter.CheckAdapter
import com.example.zhongchou3.Adapter.FundAdapter
import com.example.zhongchou3.Adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_guanli.*
import kotlinx.android.synthetic.main.top_mybar.*

class GuanliActivity : AppCompatActivity() {
    val NoCheckList=ArrayList<Fund>()
    val HaveCheckList=ArrayList<Fund>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guanli)
        val mylayoutManager = LinearLayoutManager(this)
        mylayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        myrecycleview.layoutManager = mylayoutManager
        val hclayoutManager = LinearLayoutManager(this)
        hclayoutManager.orientation = LinearLayoutManager.VERTICAL
        hcrecycleview.layoutManager = hclayoutManager

        initNoCheckFund()
        val myadapter = CheckAdapter(NoCheckList)
        myrecycleview.adapter = myadapter
        myadapter.setOnItemClickListener {
            val intent= Intent(this,FundDetail::class.java)
            intent.putExtra("fund", NoCheckList.get(it))//强转
            startActivity(intent) }

        initHaveCheckFund()
        val hcadapter = MyAdapter(HaveCheckList)
        hcrecycleview.adapter = hcadapter
        hcadapter.setOnDelet {
            val deletfund=HaveCheckList[it]

            HaveCheckList.removeAt(it)
            hcadapter.notifyItemRemoved(it)
            hcadapter.notifyItemRangeChanged(it, HaveCheckList.size - it, "removeItem")

        }
        hcadapter.setOnItemClickListener {
            val intent= Intent(this,FundDetail::class.java)
            intent.putExtra("fund", HaveCheckList.get(it))//强转
            startActivity(intent) }

        guanli_exit.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun initHaveCheckFund() {
        HaveCheckList.add(Fund("李华10","10666654","50000","白血病",R.mipmap.img,"0","1"))
        HaveCheckList.add(Fund("李华11","11666654","50000","白血病",R.mipmap.img,"0","2"))

    }

    private fun initNoCheckFund() {
        NoCheckList.add(Fund("李华10","10666654","50000","白血病",R.mipmap.img,"0","3"))
        NoCheckList.add(Fund("李华11","11666654","50000","白血病",R.mipmap.img,"0","4"))

    }


}