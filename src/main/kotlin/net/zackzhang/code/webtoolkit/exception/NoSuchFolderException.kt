package net.zackzhang.code.webtoolkit.exception

class NoSuchFolderException(folder: String): BaseException(404, "Could not find folder '$folder'")