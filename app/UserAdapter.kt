import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class UserAdapter :ListAdapter<Customer,UserAdapter.UserAdapter>(UserViewHolder())
{
    class UserAdapter(view : View): RecyclerView.ViewHolder(view)
    {
    }
    override fun onCreateViewHolder(parent : ViewGroup, viewType:Int):UserAdapter
    {
        val inflater = LayoutInflater.from(parent.context)
        return UserAdapter(inflater.inflate(
            R.layout.userlayout,parent,false
        ))
    }

    override fun onBindViewHolder(holder: UserAdapter,position: Int)
    {
        val user = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.lblName).text = user.Name
        holder.itemView.findViewById<TextView>(R.id.lblPassword).text = user.Password
        //Declaring executor to parse the url
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        //Setting up the image
        var image: Bitmap?=null
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView2)
        executor.execute {
            val imageURL = user.imageURL

            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                Log.d("AddednewUser","Image in text "+imageURL.toString())
                handler.post {
                    Log.d("AddedNewUser","Image Added")
                    imageView.setImageBitmap(image)
                }
            }
            catch (e:java.lang.Exception)
            {
                Log.d("AddNewUser","Error happened ..."+e.toString())
                e.printStackTrace()
            }
        }
    }
}
class UserViewHolder :DiffUtil.ItemCallback<UserProfile>()
{
    override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
        return oldItem.Name == newItem.Name
    }

    override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }
}