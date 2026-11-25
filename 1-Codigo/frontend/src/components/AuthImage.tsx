// ============================================================================================
// DOCUMENTACIÓN · src/components/AuthImage.tsx
//
// QUÉ ES:        Muestra una imagen que puede estar protegida.
// QUÉ HACE:      Si la imagen es del backend (/api/...) la descarga con el token; si
//                es pública la muestra directo.
// SI LO CAMBIAS: height cambia la altura por defecto de la imagen.
// ============================================================================================
import { useEffect, useState } from 'react';
import { Box, Skeleton } from '@mui/material';
import { http } from '../api/client';

/**
 * Muestra una imagen. Si está protegida por el backend (/api/...), la descarga
 * con el token JWT; si es pública (/demo/... o externa), la usa directamente.
 */
export default function AuthImage({ src, alt, height = 160 }: { src: string; alt: string; height?: number }) {
  const isProtected = src.startsWith('/api/');
  // Estado del componente: URL.
  const [url, setUrl] = useState<string | null>(isProtected ? null : src);

  useEffect(() => {
    if (!isProtected) return undefined;
    let objectUrl: string | null = null;
    http.get(src.replace(/^\/api/, ''), { responseType: 'blob' })
      .then((r) => {
        objectUrl = URL.createObjectURL(r.data as Blob);
        setUrl(objectUrl);
      })
      .catch(() => setUrl(null));
    return () => { if (objectUrl) URL.revokeObjectURL(objectUrl); };
  }, [src, isProtected]);

  if (!url) return <Skeleton variant="rectangular" height={height} />;
  return <Box component="img" src={url} alt={alt} sx={{ width: '100%', height, objectFit: 'cover', display: 'block' }} />;
}
