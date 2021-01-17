import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.AppWindowActivity
import com.example.myapplication.activities.CalendarTrainingListActivity
import com.example.myapplication.exercises.CurrentDay
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Training
import com.example.myapplication.recycler_view.SeriesAdapter

class CalendarAdapter(
    private val list: ArrayList<String>,
    private val context : AppWindowActivity,
    private val trainings : ArrayList<Training>
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.element_calendar
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(v, context)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position], position, trainings)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }



    //the class is holding the list view
    class ViewHolder(itemView: View, private val context : AppWindowActivity) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(day : String, index: Int, trainings: ArrayList<Training>)
        {
            val d: TextView = itemView.findViewById(R.id.day)
            d.text = day

            val tr : TextView = (itemView.findViewById(R.id.training))
            var id: Long = -1;
            for(training in trainings){
                if(training.hasDay(index)){
                    tr.text = training.getName()
                    id = training.getId()
                    break
                }
            }
            if(id == -1L){
                d.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70f, context.resources.displayMetrics).toInt()
                tr.height = 0
            }

            (itemView.findViewById(R.id.mainCard) as CardView).setOnClickListener{
                onClickCardListener(index)
            }
        }

        private fun onClickCardListener(day : Int){
            val intent = Intent(context, CalendarTrainingListActivity::class.java)
            intent.putExtra("day", day)
            context.startActivity(intent)
        }
    }
}