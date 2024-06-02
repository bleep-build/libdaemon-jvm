package libdaemonjvm.internal

object DefaultSocketHandler {
  def default: SocketHandler =
    Java16SocketHandler
}
