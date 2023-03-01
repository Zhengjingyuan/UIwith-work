package com.example.zhongchou3

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.inflate
import android.widget.Toast
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zhongchou3.API.AppService
import com.example.zhongchou3.Adapter.FundAdapter
import com.example.zhongchou3.Adapter.MyAdapter
import com.example.zhongchou3.entity.FundEntity
import com.example.zhongchou3.entity.ResponseEntity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fund_item.*
import kotlinx.android.synthetic.main.top_mybar.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.zip.Inflater

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private val fundList = ArrayList<Fund>()
    private val MyfundList=ArrayList<Fund>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val page=1
        val adapter = FundAdapter(fundList)
        val layoutManager = LinearLayoutManager(view.context)
        search.setOnClickListener {
            val intent= Intent(activity,SearchActivity::class.java)
            startActivity(intent)
        }
        //项目
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.41.33:8080/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .build()
        val request = retrofit.create(AppService::class.java)
        val call= request.getFund(page)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println("请求成功")
                val result = response.body().string()
                Log.d(LoginAcitiviy.TAG, "onResponse: result = $result, isSuccess = ${response.isSuccessful}" + ", error body = ${response.errorBody()?.string()}")
                val entity: FundEntity = kotlin.runCatching {
                    Gson().fromJson(result, FundEntity::class.java)
                }.getOrNull() ?: return
                val list=entity.list
                fundList.addAll(list)
                adapter.notifyDataSetChanged()



            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
//                Toast.makeText(this@HomeFragment,"请求失败, 请稍后再试!", Toast.LENGTH_SHORT).show()
                Log.d(LoginAcitiviy.TAG, "onFailure: message = ${throwable.message}")
            }
        })

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener() {
            val intent= Intent(activity,FundDetail::class.java)
            intent.putExtra("fund", fundList.get(it))//强转
            startActivity(intent)
        }

        adapter.setOnHelp {
            val fund=fundList[it]
            val intent= Intent(activity,Help::class.java)
            intent.putExtra("fund",fundList.get(it))
            startActivity(intent)

        }






    }
    private fun initFunds(){
        fundList.add(Fund("李华1","1666654","50000","白血病",R.mipmap.img,"0","1"))
        fundList.add(Fund("李华2","2666654","50000","白血病",R.mipmap.img,"0","2"))
        fundList.add(Fund("李华3","3666654","50000","白血病",R.mipmap.img,"0","3"))
        fundList.add(Fund("李华4","4666654","50000","白血病",R.mipmap.img,"0","4"))
        fundList.add(Fund("李华5","5666654","50000","白血病",R.mipmap.img,"0","5"))
        fundList.add(Fund("李华6","6666654","50000","白血病",R.mipmap.img,"0","6"))
        fundList.add(Fund("李华7","7666654","50000","白血病",R.mipmap.img,"0","7"))
        fundList.add(Fund("李华8","8666654","50000","白血病",R.mipmap.img,"0","8"))
        fundList.add(Fund("李华9","9666654","50000","白血病",R.mipmap.img,"0","9"))
        fundList.add(Fund("李华10","10666654","50000","白血病",R.mipmap.img,"0","10"))
        fundList.add(Fund("李华11","11666654","50000","白血病",R.mipmap.img,"0","11"))
    }





}