import { type IFeature } from '@/shared/model/feature.model';
import { type ICustomisationLevel } from '@/shared/model/customisation-level.model';

export interface IFeatureDeployement {
  id?: number;
  code?: string | null;
  customisationDescription?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
  feature?: IFeature | null;
  custoLevel?: ICustomisationLevel | null;
}

export class FeatureDeployement implements IFeatureDeployement {
  constructor(
    public id?: number,
    public code?: string | null,
    public customisationDescription?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
    public feature?: IFeature | null,
    public custoLevel?: ICustomisationLevel | null,
  ) {}
}
