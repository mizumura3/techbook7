package example.repository

import example.domain.Artist

/**
 * Artist を操作する repository の interface
 */
interface ArtistRepository {

    /**
     * 全件取得
     */
    fun findAll(): List<Artist>

    /**
     * ID と一致する Artist を返却する
     */
    fun findById(id: Int): Artist

    /**
     * Artist を insert する
     */
    fun create(artist: Artist): Int
}