package com.dima.vkclient.ui.profile.adapter

import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.dpToPx
import com.dima.vkclient.common.getDateTimeWholeDayText
import com.dima.vkclient.data.domain.userprofile.UserProfileItem
import com.dima.vkclient.ui.profile.adapter.career.CareerAdapter
import java.text.DateFormatSymbols

class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(profileItem: UserProfileItem) {

        itemView fillUserProfileAvatarAndLastSeen profileItem

        itemView fillUserProfileBirthday profileItem

        itemView fillUserCountryAndCity profileItem

        itemView fillUserUniversityInformation profileItem

        itemView fillUserDomainAndFollowersCount profileItem

        itemView fillUserCareer profileItem

        itemView fillUserEducation profileItem

        itemView fillUserAboutInformation profileItem

    }

    private infix fun View.fillUserProfileAvatarAndLastSeen(profileItem: UserProfileItem) {
        itemView.findViewById<TextView>(R.id.userProfileName).text = String.format(
            itemView.context.getString(R.string.two_word),
            profileItem.firstName,
            profileItem.lastName
        )

        val userProfileAvatar: ImageView = itemView.findViewById(R.id.userProfileAvatar)

        val avatarUrl =
            when (itemView.context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
                Configuration.SCREENLAYOUT_SIZE_SMALL -> {
                    profileItem.photo50
                }
                Configuration.SCREENLAYOUT_SIZE_NORMAL -> {
                    profileItem.photo100
                }
                Configuration.SCREENLAYOUT_SIZE_LARGE -> {
                    profileItem.photo100
                }
                else -> {
                    profileItem.photo100
                }
            }

        Glide.with(userProfileAvatar)
            .load(avatarUrl)
            .apply(
                RequestOptions().override(
                    Constants.PROFILE_AVATAR_SIZE.dpToPx(),
                    Constants.PROFILE_AVATAR_SIZE.dpToPx()
                )
            )
            .circleCrop()
            .into(userProfileAvatar)

        itemView.findViewById<TextView>(R.id.userProfileLastSeen).text = String.format(
            itemView.context.getString(R.string.last_seen),
            profileItem.lastSeen.time.getDateTimeWholeDayText()
        )
    }

    private infix fun View.fillUserProfileBirthday(profileItem: UserProfileItem) {
        val birthdayParts = profileItem.bdate.split(".")

        val birthdayFormatted = if (birthdayParts.size == 3) {
            "${birthdayParts[0]} ${getMonth(birthdayParts[1].toInt())} ${birthdayParts[2]}"
        } else {
            profileItem.bdate
        }

        itemView.findViewById<TextView>(R.id.userProfileBirthday).text = String.format(
            itemView.context.getString(R.string.profile_date_of_birth),
            birthdayFormatted
        )
    }

    private infix fun View.fillUserCountryAndCity(profileItem: UserProfileItem) {
        itemView.findViewById<TextView>(R.id.userProfileCountry).text =
            String.format(itemView.context.getString(R.string.profile_country), profileItem.country)

        itemView.findViewById<TextView>(R.id.userProfileCity).text =
            String.format(itemView.context.getString(R.string.profile_city), profileItem.city)
    }

    private infix fun View.fillUserUniversityInformation(profileItem: UserProfileItem) {
        if (profileItem.universityName.isEmpty()) {
            itemView.findViewById<TextView>(R.id.userProfileEducation).visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.userProfileImageEducation).visibility = View.GONE
        } else {
            itemView.findViewById<TextView>(R.id.userProfileEducation).text =
                profileItem.universityName
        }
    }

    private infix fun View.fillUserDomainAndFollowersCount(profileItem: UserProfileItem) {
        itemView.findViewById<TextView>(R.id.userProfileFollowersCount).text =
            itemView.context.resources.getQuantityString(
                R.plurals.profile_followers_count,
                profileItem.followersCount,
                profileItem.followersCount
            )

        itemView.findViewById<TextView>(R.id.userProfileDomain).text = profileItem.domain
    }

    private infix fun View.fillUserCareer(profileItem: UserProfileItem) {
        val userProfileRecyclerCareer: RecyclerView =
            itemView.findViewById(R.id.userProfileRecyclerCareer)

        if (profileItem.career.isNullOrEmpty()) {
            itemView.findViewById<View>(R.id.userProfileDividerCareer).visibility = View.GONE
            itemView.findViewById<View>(R.id.userProfileCareerTitle).visibility = View.GONE
            userProfileRecyclerCareer.visibility = View.GONE
        } else {
            val careerAdapter = CareerAdapter(profileItem.career)
            userProfileRecyclerCareer.layoutManager = LinearLayoutManager(itemView.context)
            userProfileRecyclerCareer.adapter = careerAdapter
            userProfileRecyclerCareer.isNestedScrollingEnabled = false
        }
    }

    private infix fun View.fillUserEducation(profileItem: UserProfileItem) {
        if (profileItem.universityName.isEmpty()) {
            itemView.findViewById<View>(R.id.userProfileDividerEducation).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.userProfileEducationTitle).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.userProfileUniversityTitle).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.userProfileEducationDetail).visibility = View.GONE
        } else {
            val universityName = profileItem.universityName
            val facultyName = profileItem.facultyName
            val educationForm = profileItem.educationForm ?: ""

            val education = "$universityName\n$facultyName\n$educationForm".trim()

            itemView.findViewById<TextView>(R.id.userProfileEducationDetail).text = education
        }
    }

    private infix fun View.fillUserAboutInformation(profileItem: UserProfileItem) {
        if (profileItem.about.isEmpty()) {
            itemView.findViewById<View>(R.id.userProfileDividerPersonalInformation).visibility =
                View.GONE
            itemView.findViewById<TextView>(R.id.userProfilePersonalInformationTitle).visibility =
                View.GONE
            itemView.findViewById<TextView>(R.id.userProfileAboutMeTitle).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.userProfileAboutMeDetail).visibility = View.GONE
        } else {
            itemView.findViewById<TextView>(R.id.userProfileAboutMeDetail).text = profileItem.about
        }
    }

    private fun getMonth(month: Int): String? {
        return DateFormatSymbols().months[month - 1]
    }
}