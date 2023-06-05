package onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import ru.vsu.cs.tp.paws.R

class ViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var indicator: CircleIndicator3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        viewPager = view.findViewById(R.id.view_pager)
        indicator = view.findViewById(R.id.indicator)
        val fragmentList = arrayListOf<Fragment>(
            OnboardFragmentFirst(),
            OnboardFragmentSecond(),
            OnboardFragmentThird()
        )

        val adapter = ViewPagerAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
        )
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)
        return view
    }

}