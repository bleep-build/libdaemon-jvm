package libdaemonjvm.internal

import libdaemonjvm.SocketPaths
import libdaemonjvm.errors.*

import java.net.SocketException
import java.nio.channels.SocketChannel

object SocketFile {
  def canConnect(paths: SocketPaths): Either[Throwable, Unit] = {
    var s: Either[Throwable, SocketChannel] = null
    try {
      s = connect(paths)
      s.map(_ => ())
    }
    finally if (s != null)
      s.toOption.foreach(_.close())
  }
  def connect(paths: SocketPaths): Either[Throwable, SocketChannel] = {
    var s: SocketChannel = null
    try {
      s = SocketHandler.client(paths)
      Right(s)
    }
    catch {
      case e: SocketException =>
        if (s != null)
          s.close()
        Left(e)
      case e: SocketExceptionLike =>
        if (s != null)
          s.close()
        Left(e)
    }
  }
}
