
/** blob链接转base64 */
export function urlToBase64(url, type = "image/png") {
  return new Promise((resolve, reject) => {
    let img = new Image()
    img.src = url
    img.onload = function () {
      let canvas = document.createElement("canvas")
      canvas.width = this.naturalWidth
      canvas.height = this.naturalHeight
      let ctx = canvas.getContext("2d")
      ctx.drawImage(img, 0, 0)
      let result = canvas.toDataURL(type || 'image/png')
      resolve(result)
    }
    img.onerror = function (err) {
      reject(err)
    }
  })
}
