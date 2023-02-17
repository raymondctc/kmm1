package com.ninegag.app.shared.infra.remote.tag.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.infra.remote.interest.model.ApiInterest
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlin.jvm.JvmField

class ApiNavListResponse(
    @JvmField val tagLists: Map<String, List<ApiTag>>,
    @JvmField val homeInterests: List<ApiInterest>,
    @JvmField val homeTags: List<ApiTag>
) : ApiBaseResponse()

@Serializable
data class NavTagList(
    @JvmField val tags: List<ApiTag>
)

@Serializable
class NavHomeList(
    @JvmField val interests: List<ApiInterest>,
    @JvmField val tags: List<ApiTag>
)

object ApiNavListHomeDataSerializer: KSerializer<NavHomeList> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(ApiNavListHomeDataSerializer::class.qualifiedName!!) {
            element<List<ApiTag>>("tags")
            element<List<ApiInterest>>("interests")
        }

    override fun deserialize(decoder: Decoder): NavHomeList {
        return decoder.decodeStructure(descriptor) {
            var tagLists: List<ApiTag> = emptyList()
            var interestLists: List<ApiInterest> = emptyList()
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> tagLists = decodeSerializableElement(descriptor, 0, ListSerializer(ApiTag.serializer()))
                    1 -> interestLists = decodeSerializableElement(descriptor, 1, ListSerializer(ApiInterest.serializer()))
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index $index")
                }
            }
            NavHomeList(interestLists, tagLists)
        }
    }

    override fun serialize(encoder: Encoder, value: NavHomeList) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, ListSerializer(ApiTag.serializer()), value.tags)
            encodeSerializableElement(descriptor, 1, ListSerializer(ApiInterest.serializer()), value.interests)
        }
    }
}

class ApiNavListDataSerializer(
    private val navListHomeDataSerializer: ApiNavListHomeDataSerializer
): KSerializer<Map<String, Any>> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(ApiNavListDataSerializer::class.qualifiedName!!) {
            element<NavTagList>("popular")
            element<NavTagList>("trending")
            element<NavTagList>("other")
            element<NavHomeList>("home")
        }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Map<String, Any> {
        return decoder.decodeStructure(descriptor) {
            val map = LinkedHashMap<String, Any>()
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0, 1, 2, 3 -> {
                        if (descriptor.getElementName(index) == "home") {
                            val navTagList = decodeSerializableElement(descriptor, index, NavHomeList.serializer())
                            map[descriptor.getElementName(index)] = navTagList
                        } else {
                            val navTagList = decodeSerializableElement(descriptor, index, NavTagList.serializer())
                            map[descriptor.getElementName(index)] = navTagList
                        }
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index $index")
                }
            }
            map
        }
    }

    override fun serialize(encoder: Encoder, value: Map<String, Any>) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, NavTagList.serializer(), value["popular"] as NavTagList)
            encodeSerializableElement(descriptor, 1, NavTagList.serializer(), value["trending"] as NavTagList)
            encodeSerializableElement(descriptor, 2, NavTagList.serializer(), value["other"] as NavTagList)
            encodeSerializableElement(descriptor, 3, navListHomeDataSerializer, value["home"] as NavHomeList)
        }
    }
}

//@OptIn(ExperimentalSerializationApi::class)
//@Serializer(
//    forClass = ApiNavListResponse::class
//)
class ApiNavListSerializer(
    private val apiNavListDataSerializer: ApiNavListDataSerializer
): KSerializer<ApiNavListResponse> {

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(ApiNavListResponse::class.qualifiedName!!) {
            element<ApiBaseResponse.Meta>("meta")
            element("data", apiNavListDataSerializer.descriptor)
        }

    override fun deserialize(decoder: Decoder): ApiNavListResponse {
        return decoder.decodeStructure(descriptor) {
            val metaSerializer = ApiBaseResponse.Meta.serializer()
            var meta: ApiBaseResponse.Meta? = null
            var data: Map<String, Any> = emptyMap()

            val tagLists: MutableMap<String, List<ApiTag>> = mutableMapOf()
            var homeTagList: List<ApiTag> = emptyList()
            var homeInterestList: List<ApiInterest> = emptyList()

            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> meta = decodeSerializableElement(metaSerializer.descriptor, 0, metaSerializer)
                    1 -> data = decodeSerializableElement(apiNavListDataSerializer.descriptor, 1, apiNavListDataSerializer)
                    CompositeDecoder.DECODE_DONE -> break@loop
                    else -> throw SerializationException("Unexpected index $index")
                }
            }

            data.forEach {
                if (it.key == "home") {
                    homeTagList = (it.value as NavHomeList).tags
                    homeInterestList = (it.value as NavHomeList).interests
                } else {
                    tagLists[it.key] = (it.value as NavTagList).tags
                }
            }

            val resp = ApiNavListResponse(tagLists, homeInterestList, homeTagList)
            resp.meta = meta
            resp
        }
    }

    override fun serialize(encoder: Encoder, value: ApiNavListResponse) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, ApiBaseResponse.Meta.serializer(), value.meta!!)
            encodeSerializableElement(descriptor, 1, MapSerializer(String.serializer(), ListSerializer(ApiTag.serializer())), value.tagLists)
            encodeSerializableElement(descriptor, 2, ListSerializer(ApiInterest.serializer()), value.homeInterests)
            encodeSerializableElement(descriptor, 3, ListSerializer(ApiTag.serializer()), value.homeTags)
        }
    }
}