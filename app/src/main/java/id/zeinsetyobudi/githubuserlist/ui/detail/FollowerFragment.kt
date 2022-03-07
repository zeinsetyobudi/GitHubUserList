package id.zeinsetyobudi.githubuserlist.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.zeinsetyobudi.githubuserlist.User
import id.zeinsetyobudi.githubuserlist.apicontroller.FollowResponseItem
import id.zeinsetyobudi.githubuserlist.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private lateinit var actionFollowerBinding: FragmentFollowerBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val person = activity?.intent?.getParcelableExtra<User>(EXTRA_USER) as User

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        detailViewModel.stateFollower.observe(this, {
            it.getContentIfNotHandled()?.let {
                person.username?.let { it -> detailViewModel.getFollowerList(it) }
            }
        })

        detailViewModel.listFollower.observe(this, { followerList ->
            setRecycleFollowerList(followerList)
        })

        detailViewModel.isLoadingFollower.observe(this, {
            showLoading(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        actionFollowerBinding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return actionFollowerBinding.root
    }

    private fun setRecycleFollowerList(followers: List<FollowResponseItem>) {
        val listFollower = ArrayList<Follower>()
        for (users in followers) {
            val follower = Follower(
                users.login,
                users.avatarUrl
            )
            listFollower.add(follower)
        }

        actionFollowerBinding.rvFollower.layoutManager = LinearLayoutManager(context)
        val listFollowerAdapter = FollowerAdapter(listFollower)
        actionFollowerBinding.rvFollower.adapter = listFollowerAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        actionFollowerBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}