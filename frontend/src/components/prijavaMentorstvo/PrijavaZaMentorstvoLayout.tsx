import PrijavaModal from "./components/PrijavaModal";

const userId = 1; //potrebno popravit

const PrijavaZaMentorstvoLayout = ({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) => {
  return <PrijavaModal requesterId={userId} open={open} onClose={onClose} />;
};

export default PrijavaZaMentorstvoLayout;
