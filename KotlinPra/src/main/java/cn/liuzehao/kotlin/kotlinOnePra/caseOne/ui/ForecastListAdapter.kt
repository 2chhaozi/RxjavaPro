package cn.liuzehao.kotlin.kotlinOnePra.caseOne.ui

/**
 * Created by liuzehao on 2019-09-12.
 */
/*
class ForecastListAdapter(val weekForecast: ForecastList,
                          val itemClick: (Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    */
/**
     *当表达式只有一个时能用等号代替括号
     *//*

    override fun getItemCount(): Int = weekForecast.dailyForcast.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast.dailyForcast[position])
    }

    class ViewHolder(view: View, val itemClick: (Forecast) -> Unit): RecyclerView.ViewHolder(view){

        */
/**
         *with接收一个对象和一个扩展函数作为它的参数，然后使这个对象扩展这个函数。
         *这表示所有我们在括号中编写的代码都是作为对象（第一个参数）的一个扩展函数，
         *我们可以像作为this一样使用所有它的public方法和属性
         *//*

        fun bindForecast(forecast: Forecast){
            with(forecast){
                Glide.with(itemView.context).load(iconUrl).into(itemView.icon)
                itemView.date.text = date
                itemView.description.text = description
                itemView.maxTemperature.text = "${high.toString()}"
                itemView.minTemperature.text = "${low.toString()}"
                itemView.setOnClickListener { itemClick(forecast) }
            }
        }
    }

    interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }

    */
/**
     *这里使用val表示定义，表示构造函数类型的同时并定义对应参数的变量
     *同时将传递过来的变量同时传递给父类，类似super(xxx)
     *//*
*/
/*
    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView){

    }*//*


}*/
