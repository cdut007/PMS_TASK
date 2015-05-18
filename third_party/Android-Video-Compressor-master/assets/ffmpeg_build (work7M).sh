#!/bin/bash

. abi_settings.sh $1 $2 $3

pushd ffmpeg

case $1 in
  armeabi-v7a | armeabi-v7a-neon)
    CPU='cortex-a8'
  ;;
  x86)
    CPU='i686'
  ;;
esac

make clean

./configure \
--target-os="$TARGET_OS" \
--cross-prefix="$CROSS_PREFIX" \
--arch="$NDK_ABI" \
--cpu="$CPU" \
--enable-runtime-cpudetect \
--sysroot="$NDK_SYSROOT" \
--enable-small \
--disable-encoders \
--disable-decoders \
--disable-protocols \
--enable-decoder=mp3 \
--enable-decoder=vorbis \
--enable-decoder=alac \
--enable-decoder=mpeg4 \
--enable-decoder=vp8 \
--enable-decoder=flac \
--enable-decoder=pcm_s8 \
--enable-decoder=pcm_u8 \
--enable-decoder=h264 \
--enable-decoder=h263 \
--enable-encoder=aac \
--enable-decoder=aac \
--enable-decoder=amrnb \
--enable-decoder=amrwb \
--enable-encoder=pcm_s16le \
--enable-decoder=pcm_s16le \
--enable-decoder=mjpeg \
--enable-decoder=rawvideo \
--enable-encoder=libx264 \
--enable-libx264 \
--enable-encoder=mpeg4 \
--enable-encoder=h264 \
--disable-network \
--enable-protocol=rtmps \
--enable-protocol=rtmpt \
--enable-protocol=crypto \
--enable-protocol=rtmpte \
--enable-protocol=file \
--enable-protocol=pipe \
--enable-protocol=rtp \
--enable-protocol=rtmp \
--enable-protocol=tcp \
--enable-protocol=http \
--enable-protocol=rtmpe \
--enable-protocol=udp \
--enable-pic \
--disable-libass \
--disable-libfreetype \
--disable-libfribidi \
--disable-fontconfig \
--enable-pthreads \
--disable-debug \
--disable-ffserver \
--enable-version3 \
--enable-hardcoded-tables \
--disable-ffplay \
--disable-ffprobe \
--enable-gpl \
--enable-yasm \
--disable-doc \
--disable-shared \
--enable-static \
--pkg-config="${2}/ffmpeg-pkg-config" \
--prefix="${2}/build/${1}" \
--extra-cflags="-I${TOOLCHAIN_PREFIX}/include $CFLAGS" \
--extra-ldflags="-L${TOOLCHAIN_PREFIX}/lib $LDFLAGS" \
--extra-libs="-lpng -lexpat -lm" \
--extra-cxxflags="$CXX_FLAGS" || exit 1

make -j${NUMBER_OF_CORES} && make install || exit 1

popd
