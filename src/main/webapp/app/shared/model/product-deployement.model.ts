import { type IClient } from '@/shared/model/client.model';
import { type IProduct } from '@/shared/model/product.model';
import { type IDeployementType } from '@/shared/model/deployement-type.model';
import { type IHA } from '@/shared/model/ha.model';
import { type IHSM } from '@/shared/model/hsm.model';
import { type IDB } from '@/shared/model/db.model';
import { type IHOST } from '@/shared/model/host.model';
import { type IApplicationServer } from '@/shared/model/application-server.model';
import { type IOS } from '@/shared/model/os.model';

export interface IProductDeployement {
  id?: number;
  code?: string | null;
  refContract?: string | null;
  startDeployementDate?: Date | null;
  endDeployementDate?: Date | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
  client?: IClient | null;
  product?: IProduct | null;
  deployementType?: IDeployementType | null;
  ha?: IHA | null;
  hsm?: IHSM | null;
  db?: IDB | null;
  host?: IHOST | null;
  applicationServer?: IApplicationServer | null;
  os?: IOS | null;
}

export class ProductDeployement implements IProductDeployement {
  constructor(
    public id?: number,
    public code?: string | null,
    public refContract?: string | null,
    public startDeployementDate?: Date | null,
    public endDeployementDate?: Date | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
    public client?: IClient | null,
    public product?: IProduct | null,
    public deployementType?: IDeployementType | null,
    public ha?: IHA | null,
    public hsm?: IHSM | null,
    public db?: IDB | null,
    public host?: IHOST | null,
    public applicationServer?: IApplicationServer | null,
    public os?: IOS | null,
  ) {}
}
