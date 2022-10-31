object Package {
    const val group = "com.sladewatkins"
    const val name = "SRW Watchtower"
    const val id = "$group.sladewatkins"
    val versionName =
        "${Version.main}.${Version.mirror}.${Version.patch}${if (Version.revision.isNotEmpty()) "-${Version.revision}" else ""}"
    const val copyright = "Copyright (C) TwidereProject and Contributors"
    const val versionCode = Version.build

    object Version {
        const val main = "0"
        const val mirror = "1"
        const val patch = "0"
        const val revision = ""
        const val build = 2
    }
}
