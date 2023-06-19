package com.jairorr.samplesocialmediajc.network

import com.jairorr.samplesocialmediajc.data.MemberUser

class GetRandomMembers(private val socialMediaService: SocialMediaService) {
    suspend operator fun invoke(): SocialMediaResult<List<MemberUser>> =
        handleApi { socialMediaService.getRandomMembers(offset = (1..15).random()) }
}
