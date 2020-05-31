package example.repository

import example.domain.Artist

/**
 * Artist を操作する repository の interface
 */
interface ArtistsRepository {

    /**
     * 全件取得
     */
    fun all(): List<Artist>

    /**
     * ID と一致する Artist を返却する
     */
    fun findById(id: Int): Artist

    /**
     * Artist を insert する
     */
    fun create(artist: Artist): Int

    /**
     * Artists の件数を返却する
     */
    fun count(): Int
}
