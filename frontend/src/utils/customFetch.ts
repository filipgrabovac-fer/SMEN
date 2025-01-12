type CustomFetchType = {
  endpointUrl: string;
  method: string;
  body?: Record<string, any> | any;
  headers?: Record<string, string>;
};

export const customFetch = async ({
  endpointUrl,
  method,
  body,
  headers,
}: CustomFetchType) => {
  const data = await fetch(endpointUrl, {
    redirect: "follow",
    method,
    body: body,
    headers,
  }).then((data) => data.json());

  return data;
};
