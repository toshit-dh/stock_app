package com.toshitdev.stocksapp.presentation.company_lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toshitdev.stocksapp.domain.model.CompanyList

@Composable
fun CompanyItem(
    company: CompanyList,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = company.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Text(
                    text = company.exchange,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = "( ${company.symbol})",
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CompanyItemPreview() {
    CompanyItem(company = CompanyList(
        symbol = "AAPL",
        name = "Apple Inc.",
        exchange = "NASDAQ"
    ))
}

