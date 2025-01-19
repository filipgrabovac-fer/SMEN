import { Button } from "antd";
import OglasiHeader from "./components/OglasiHeader";
import OglasiTable from "./components/OglasiTable";
import { useGetPosts } from "./hooks/useGetPosts.hook";
import { TrashIcon } from "@heroicons/react/24/solid";
import { useState } from "react";
import { EditOglasModal } from "./components/EditOglasModal.component";
import { useQueryClient } from "@tanstack/react-query";
import { useDeleteOglas } from "./hooks/useDeleteOglas.hook";

const OglasOverview = () => {
  const { data: postsData } = useGetPosts();

  const [selectedOglasId, setSelectedOglasId] = useState<number | undefined>();
  const queryClient = useQueryClient();

  const { mutate: deleteOglas } = useDeleteOglas({
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["posts"] }),
  });

  const canDeletePost = localStorage.getItem("userRole") != "USER";

  const data =
    postsData?.map((post, i) => ({
      key: i.toString(),
      autor: post.author,
      opis: post.description,
      datum: new Date(),
      naslovOglasa: post.createdAt.slice(0, 10),
      details: `https://example.com/${post.id}`,
      edit: (
        <div className="flex gap-x-2">
          {canDeletePost && (
            <Button className="p-auto">
              <TrashIcon
                className="w-4 h-4"
                color="red"
                onClick={() => deleteOglas({ oglasId: post.id })}
              />
            </Button>
          )}

          <EditOglasModal
            title={post.title}
            description={post.description}
            selectedOglasId={selectedOglasId}
            setSelectedOglasId={setSelectedOglasId}
          />
        </div>
      ),
    })) ?? [];

  return (
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <OglasiHeader />
      <OglasiTable data={data} />
    </div>
  );
};

export default OglasOverview;
