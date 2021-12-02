package kr.foodie.domain.shopItem;

import lombok.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
public class ExistImages {

    private String existImages;
}
