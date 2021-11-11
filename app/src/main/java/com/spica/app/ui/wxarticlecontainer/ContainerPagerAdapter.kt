package com.spica.app.ui.wxarticlecontainer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ContainerPagerAdapter(fragment: Fragment,
                            val fragments: List<Fragment>) : FragmentStateAdapter(fragment) {


  override fun getItemCount(): Int = fragments.size


  override fun createFragment(position: Int): Fragment {
    return fragments[position]
  }


}